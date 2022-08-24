//
//  IntroView.swift
//  iosApp
//
//  Created by Данил Ломаев on 24.08.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import PaymentKit

struct IntroView: View {
    @Binding var isShowedIntro: Bool
    @State private var process1: CGFloat = 40
    @State private var process2: CGFloat = 227
    @State private var process3: CGFloat = 99

    var body: some View {
        VStack {
            Text("Добро пожаловать в Коммуналку!")
                .font(.title)
                .bold()
                .multilineTextAlignment(.center)
                .padding(8)
                .transition(.slide)
            Spacer()
            
            Text("Ваши платежи по коммунальным услугам всегда под контролем!")
                .font(.system(size: 15, weight: .semibold, design: .rounded))
                .multilineTextAlignment(.center)
                .padding()
            
            HStack {
                RoundedRectangle(cornerRadius: 16)
                    .fill(Color(.systemGray6))
                    .frame(width: 80, height: 90, alignment: .center)
                    .modifier(AnimatingNumberOverlay(number: process1))
                
                RoundedRectangle(cornerRadius: 16)
                    .fill(Color(.systemGray6))
                    .frame(width: 80, height: 90, alignment: .center)
                    .modifier(AnimatingNumberOverlay(number: process2))
                
                RoundedRectangle(cornerRadius: 16)
                    .fill(Color(.systemRed))
                    .frame(width: 80, height: 90, alignment: .center)
                    .modifier(AnimatingNumberOverlay(number: process3))
            }
            .padding()
            
            Text("Легко добавляйте и следите за счетчиками!")
                .font(.system(size: 15, weight: .semibold, design: .rounded))
                .multilineTextAlignment(.center)
                .padding()
            
            PaymentRow(payment: PaymentModel(
                id: "000",
                hotWaterCount: 32,
                coldWaterCount: 31,
                electricity: 30,
                date: "12.02.2099")
            )
            .padding(12)
            .background(
                Color(.systemGray6).cornerRadius(16)
            )
            .padding(8)
            .padding(.horizontal, 2)
            .transition(.identity)
            
            Button(action: {
                isShowedIntro = true
            }) {
                Text("Погнали!")
            }
            .tint(.blue)
            .buttonStyle(.borderedProminent)
            .buttonBorderShape(.roundedRectangle(radius: 16))
            .controlSize(.large)

            Spacer()
        }
        .onAppear {
            DispatchQueue.main.async {
                withAnimation(.easeIn.speed(0.024)) {
                    process1 = 100
                }
                
                withAnimation(.easeIn.speed(0.03)) {
                    process2 = 372
                }
                
                withAnimation(.easeIn.speed(0.02)) {
                    process3 = 102
                }
            }
        }
    }
}


struct AnimatingNumberOverlay: Animatable, ViewModifier {
    var number: CGFloat
    var animatableData: CGFloat {
        get {
            number
        }
        
        set {
            number = newValue
        }
    }
    
    func body(content: Content) -> some View {
        content
            .overlay(
                Text(Int(number).description)
                    .font(.largeTitle)
            )
    }
}

struct IntroView_Previews: PreviewProvider {
    static var previews: some View {
        IntroView(isShowedIntro: .constant(false))
    }
}
