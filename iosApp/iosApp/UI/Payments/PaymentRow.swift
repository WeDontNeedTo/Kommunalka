//
//  PaymentRow.swift
//  Kommunalka
//
//  Created by Данил Ломаев on 28.05.2022.
//

import SwiftUI
import PaymentKit

struct PaymentRow: View {
    let payment: PaymentModel
    
    var body: some View {
        VStack(alignment: .leading) {
            Text("Отчет за \(payment.date)")
                .font(.title2)
                .bold()
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.bottom, 8)
            
            VStack(alignment: .leading, spacing: 8) {
                HStack {
                    Label("Горячая вода: ", systemImage: "flame.circle")
                    Text("\(payment.hotWaterCount.description) куб/м")
                }
                .foregroundColor(.red)
                
                HStack {
                    Label("Холодная вода: ", systemImage: "humidity.fill")
                    Text("\(payment.coldWaterCount.description) куб/м")
                }
                .foregroundColor(.cyan)
                
                HStack {
                    Label("Электроэнергия: ", systemImage: "bolt.square.fill")
                    Text("\(payment.electricity.description) кВт/ч")
                }
                .foregroundColor(.orange)
            }
            .font(.headline)
        }
        .padding(.vertical)
    }
}
