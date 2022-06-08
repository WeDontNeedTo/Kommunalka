//
//  PaymentRepository.swift
//  Kommunalka
//
//  Created by Данил Ломаев on 30.05.2022.
//

import Foundation

protocol PaymentRepositoryProtocol {
    func savePayments(with model: [PaymentModel])
    func getPayment() -> [PaymentModel]
    func updatePayment(with paymentId: UUID, and model: PaymentModel) -> [PaymentModel]
    func sumFor(payment: PaymentModel) -> Float
}

class PaymentRepository: PaymentRepositoryProtocol {
    
    private let decoder = JSONDecoder()
    private let defaults = UserDefaults()
    private let paymentKey: String = "payments"
    
    func savePayments(with model: [PaymentModel]) {
        guard let encoded = try? PropertyListEncoder().encode(model) else { return }
        defaults.set(encoded, forKey: self.paymentKey)
    }
    
    func getPayment() -> [PaymentModel] {
        if let data = defaults.data(forKey: self.paymentKey) {
            return try! PropertyListDecoder().decode([PaymentModel].self, from: data)
        } else {
            return []
        }
    }
        
    func updatePayment(with paymentId: UUID, and model: PaymentModel) -> [PaymentModel] {
        var payments = self.getPayment()
        if let updatedIndex = payments.firstIndex(where: { $0.id == paymentId }) {
            payments[updatedIndex] = model
        }
        self.savePayments(with: payments)
        return payments
    }
    
    func sumFor(payment: PaymentModel) -> Float {
        (Float(payment.electricity) * Tarrif.electricityPrice) + (Float(payment.coldWaterCount) * Tarrif.coldWaterPrice) + (Float(payment.hotWaterCount) * Tarrif.hotWaterPrice)
    }
}
