//
//  PaymentRepository.swift
//  Kommunalka
//
//  Created by Данил Ломаев on 30.05.2022.
//

import Foundation

protocol PaymentRepositoryProtocol {
    func savePayments(with model: [PaymentModelObject])
    func getPayment() -> [PaymentModelObject]
    func updatePayment(with paymentId: UUID, and model: PaymentModelObject) -> [PaymentModelObject]
    func sumFor(payment: PaymentModelObject) -> Float
}

class PaymentRepository: PaymentRepositoryProtocol {
    
    private let decoder = JSONDecoder()
    private let defaults = UserDefaults()
    private let paymentKey: String = "payments"
    
    func savePayments(with model: [PaymentModelObject]) {
        guard let encoded = try? PropertyListEncoder().encode(model) else { return }
        defaults.set(encoded, forKey: self.paymentKey)
    }
    
    func getPayment() -> [PaymentModelObject] {
        if let data = defaults.data(forKey: self.paymentKey) {
            return try! PropertyListDecoder().decode([PaymentModelObject].self, from: data)
        } else {
            return []
        }
    }
        
    func updatePayment(with paymentId: UUID, and model: PaymentModelObject) -> [PaymentModelObject] {
        var payments = self.getPayment()
        if let updatedIndex = payments.firstIndex(where: { $0.id == paymentId }) {
            payments[updatedIndex] = model
        }
        self.savePayments(with: payments)
        return payments
    }
    
    func sumFor(payment: PaymentModelObject) -> Float {
        (Float(payment.electricity) * Tarrif.electricityPrice) + (Float(payment.coldWaterCount) * Tarrif.coldWaterPrice) + (Float(payment.hotWaterCount) * Tarrif.hotWaterPrice)
    }
}
