//
//  SumPayCard.swift
//  Kommunalka
//
//  Created by Данил Ломаев on 30.05.2022.
//

import SwiftUI

struct SumPayCard: View {
    @Binding var previousSum: Float
    @Binding var currentSum: Float
    @Binding var sum: Float
    
    private var numberFormatter: NumberFormatter {
        let formatter = NumberFormatter()
        formatter.locale = Locale(identifier: "ru_RU") // Change this to another locale if you want to force a specific locale, otherwise this is redundant as the current locale is the default already
        formatter.numberStyle = .currency
        return formatter
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Сумма за прошлый месяц: \(numberFormatter.string(from: previousSum as NSNumber) ?? "0.0")")
                .font(.headline)
                .foregroundColor(.secondary)

            Text("Сумма за текущий месяц: \(numberFormatter.string(from: currentSum as NSNumber) ?? "0.0")")
                .font(.headline)

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
        SumPayCard(previousSum: .constant(600), currentSum: .constant(600), sum: .constant(600))
    }
}
