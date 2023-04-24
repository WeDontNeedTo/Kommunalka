//
//  PaymentsViewModel.swift
//  Kommunalka
//
//  Created by Данил Ломаев on 30.05.2022.
//

import Foundation
import PaymentKit

@MainActor
class PaymentViewModel: ObservableObject {
    private let paymentRepository: PaymentRepositoryProtocol
    
    init(paymentRepository: PaymentRepositoryProtocol) {
        self.paymentRepository = paymentRepository
    }
    
    @Published var paymentsList: [PaymentModel] = []
    @Published var editingPayment: PaymentModel = PaymentModel(id: UUID().uuidString, hotWaterCount: 0, coldWaterCount: 0, electricity: 0, date: "")
    @Published var sumForPay: Float = .zero
    @Published var isEditing: Bool = false
    @Published var isLoading: Bool = false
    @Published var selectedDate = Date()
    
    @MainActor
    func fetchPaymentList(forceReload: Bool = false) async {
        self.isLoading = true
        await self.getPayments(forceReload: forceReload)
        self.calculatePay()
        self.isLoading = false
    }
    
    func onPaymentDoneButtonPressed() async {
        if self.isEditing {
            await self.updateRow(model: self.editingPayment)
        } else {
            await self.addPayment(with: self.editingPayment)
        }
    }
    
    func calculateDateField(with dateFormatter: DateFormatter) {
        if self.editingPayment.date.isEmpty {
            self.editingPayment.date = dateFormatter.string(from: Date())
        } else {
            self.selectedDate = dateFormatter.date(from: self.editingPayment.date) ?? Date()
        }
    }
    
    func onEditSheetDismissed() {
        self.editingPayment = PaymentModel(id: UUID().uuidString, hotWaterCount: 0, coldWaterCount: 0, electricity: 0, date: "")
        self.isEditing = false
        self.selectedDate = Date()
    }
    
    func editFor(_ payment: PaymentModel) {
        self.isEditing = true
        self.editingPayment = payment
    }
    
    func deleteRow(with element: PaymentModel) async {
        let result = await self.paymentRepository.deletePayment(with: element.id)
        if result {
            await self.getPayments(forceReload: true)
            self.calculatePay()
        }
    }
}

// MARK: - private methods
extension PaymentViewModel {
    private func getPayments(forceReload: Bool) async {
        guard let result = try? await paymentRepository.getPayment(forceReload: forceReload) else { return }
        self.paymentsList = result
    }

    private func calculatePay() {
        self.sumForPay = 0.0
        guard paymentsList.count > 1 else { return }
        let monthsSlice = paymentsList.dropFirst(paymentsList.count - 2)
        guard monthsSlice.count == 2 else { return }
        let differenceMonthArray = Array(monthsSlice)
        self.sumForPay = Float(self.paymentRepository.calculate(currentPayment: differenceMonthArray[1], previousPayment: differenceMonthArray[0]))
    }
    
    private func addPayment(with model: PaymentModel) async {
        if (try? await paymentRepository.createPayment(with: model)) != nil {
            await self.getPayments(forceReload: true)
            self.calculatePay()
        }
    }
    
    private func updateRow(model: PaymentModel) async {
        if (try? await paymentRepository.updatePayment(
            hotWaterCount: model.hotWaterCount,
            coldWaterCount: model.coldWaterCount,
            electicity: model.electricity,
            date: model.date
        )) != nil {
            await self.getPayments(forceReload: true)
            self.calculatePay()
        }
    }
}
