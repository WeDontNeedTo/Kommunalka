//
//  PaymentRepository.swift
//  Kommunalka
//
//  Created by Данил Ломаев on 30.05.2022.
//

import Foundation
import PaymentKit

protocol PaymentRepositoryProtocol {
    func getPayment(forceReload: Bool) async throws -> [PaymentModel]
    func updatePayment(hotWaterCount: Int32?, coldWaterCount: Int32?, electicity: Int32?, date: String?) async throws -> PaymentModel?
    func createPayment(with model: PaymentModel) async throws -> PaymentModel?
    func deletePayment(with paymentId: String) async -> Bool
    func calculate(currentPayment: PaymentModel, previousPayment: PaymentModel) -> Double
}

class PaymentRepository: PaymentRepositoryProtocol {
    private let paymentManager = PaymentManager(databaseDriverFactory: DatabaseDriverFactory())
    
    func getPayment(forceReload: Bool) async throws -> [PaymentModel] {
        return try await paymentManager.getPayments(forceReload: forceReload)
    }
    
    func createPayment(with model: PaymentModel) async throws -> PaymentModel? {
        try await paymentManager.createPayment(paymentModel: model)
    }
        
    func updatePayment(hotWaterCount: Int32? = nil, coldWaterCount: Int32? = nil, electicity: Int32? = nil, date: String? = nil) async throws -> PaymentModel? {
        let patchModel = PaymentPatchDTO(
            hotWaterCount: hotWaterCount?.asKotlinInt,
            coldWaterCount: coldWaterCount?.asKotlinInt,
            electricity: electicity?.asKotlinInt,
            date: date
        )
        return try await paymentManager.updatePayment(patchDTO: patchModel)
    }
    
    func deletePayment(with paymentId: String) async -> Bool {
        do {
            return try await paymentManager.deletePayment(paymentId: paymentId) as! Bool
        } catch {
            return false
        }
    }
    
    func calculate(currentPayment: PaymentModel, previousPayment: PaymentModel) -> Double {
        self.paymentManager.calculate(currentMonthPaymentModel: currentPayment, previousMonthPaymentModel: previousPayment)
    }
}
