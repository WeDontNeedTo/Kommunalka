//
//  PaymentModel.swift
//  Kommunalka
//
//  Created by Данил Ломаев on 28.05.2022.
//

import Foundation

struct PaymentModelObject: Codable, Identifiable, Equatable {
    var id = UUID()
    var hotWaterCount: Int
    var coldWaterCount: Int
    var electricity: Int
    var date: String
}
