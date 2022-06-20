//
//  SumPayCard.swift
//  Kommunalka
//
//  Created by Данил Ломаев on 30.05.2022.
//

import SwiftUI
import PaymentKit

struct SumPayCard: View {
    @Binding var sum: Float
    
    private var numberFormatter: NumberFormatter {
        let formatter = NumberFormatter()
        formatter.locale = Locale(identifier: "ru_RU") // Change this to another locale if you want to force a specific locale, otherwise this is redundant as the current locale is the default already
        formatter.numberStyle = .currency
        return formatter
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Тарифы: ")
                .font(.headline)
            
            Text("Холодная вода: \(Tarrif.shared.COLD_WATER_PRICE.description) за куб/м3")
                .font(.headline)
                .foregroundColor(.secondary)
            
            Text("Горячая вода: \(Tarrif.shared.HOT_WATER_PRICE.description) за куб/м3")
                .font(.headline)
                .foregroundColor(.secondary)
            
            Text("Электричество: \(Tarrif.shared.ELECTRICITY_PRICE.description) за кВт/ч")
                .font(.headline)
                .foregroundColor(.secondary)
            
            Divider()
            
            Text("Текущий платеж: \(numberFormatter.string(from: sum as NSNumber) ?? "0.0")")
                .font(.title3)
                .bold()
                .foregroundColor(.green)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(.vertical)
    }
}

struct SumPayCard_Previews: PreviewProvider {
    static var previews: some View {
        SumPayCard(sum: .constant(600))
    }
}
