//
//  PaymentsViewModel.swift
//  Kommunalka
//
//  Created by Данил Ломаев on 30.05.2022.
//

import Foundation

class PaymentViewModel: ObservableObject {
    
    private let paymentRepository: PaymentRepositoryProtocol
    
    init(paymentRepository: PaymentRepositoryProtocol) {
        self.paymentRepository = paymentRepository
    }
    
    @Published var paymentsList: [PaymentModel] = []
    @Published var editingPayment: PaymentModel = PaymentModel(hotWaterCount: 0, coldWaterCount: 0, electricity: 0, date: "")
    @Published var previousPay: Float = .zero
    @Published var currentPay: Float = .zero
    @Published var sumForPay: Float = .zero
    @Published var isEditing: Bool = false
    @Published var selectedDate = Date()
    
    
    func onViewAppear() {
        self.getPayments()
        self.calculatePay()
    }
    
    func onPaymentDoneButtonPressed() {
        if self.isEditing {
            self.updateRow(with: self.editingPayment.id, and: self.editingPayment)
        } else {
            self.addPayment(with: self.editingPayment)
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
        self.editingPayment = PaymentModel(hotWaterCount: 0, coldWaterCount: 0, electricity: 0, date: "")
        self.isEditing = false
        self.selectedDate = Date()
    }
    
    func editFor(_ payment: PaymentModel) {
        self.isEditing = true
        self.editingPayment = payment
    }
    
    func deleteRow(with element: PaymentModel) {
        self.paymentsList = self.paymentsList.filter { $0.id != element.id }
        self.paymentRepository.savePayments(with: self.paymentsList)
        self.calculatePay()
    }
}

// MARK: - private methods
extension PaymentViewModel {
    private func getPayments() {
        self.paymentsList = self.paymentRepository.getPayment()
    }
    
    private func calculateMonthPay(for payment: PaymentModel) -> Float {
        return paymentRepository.sumFor(payment: payment)
    }
    
    private func calculatePay() {
        guard paymentsList.count > 1 else { return }
        let monthsSlice = paymentsList.dropFirst(paymentsList.count - 2)
        guard monthsSlice.count == 2 else { return }
        let differenceMonthArray = Array(monthsSlice)
        self.previousPay = paymentRepository.sumFor(payment: differenceMonthArray[0])
        self.currentPay = paymentRepository.sumFor(payment: differenceMonthArray[1])
        self.sumForPay =  self.currentPay - self.previousPay
    }
    
    private func addPayment(with model: PaymentModel) {
        paymentsList.append(model)
        self.paymentRepository.savePayments(with: paymentsList)
        self.calculatePay()
    }
    
    private func updateRow(with paymentId: UUID, and model: PaymentModel) {
        self.paymentsList = self.paymentRepository.updatePayment(with: paymentId, and: model)
        self.calculatePay()
    }
}
