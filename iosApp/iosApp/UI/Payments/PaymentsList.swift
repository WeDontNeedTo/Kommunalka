//
//  PaymentsList.swift
//  Kommunalka
//
//  Created by Данил Ломаев on 28.05.2022.
//

import SwiftUI

struct PaymentsList: View {
    @StateObject var paymentViewModel: PaymentViewModel = PaymentViewModel(paymentRepository: PaymentRepository())
    @State private var isPresented: Bool = false
    @State private var testString: String = ""

    
    var body: some View {
        NavigationView {
            List {
                Section {
                    Text("String is \(testString)")
                    SumPayCard(
                        previousSum: $paymentViewModel.previousPay,
                        currentSum: $paymentViewModel.currentPay,
                        sum: $paymentViewModel.sumForPay
                    )
                }
                
                Section {
                    if paymentViewModel.paymentsList.isEmpty {
                        Text("Нету плажетей")
                            .font(.headline)
                    } else {
                        ForEach(paymentViewModel.paymentsList) { payment in
                            PaymentRow(payment: payment)
                                .swipeActions(edge: .trailing, allowsFullSwipe: false, content: {
                                    Button(role: .destructive) {
                                        paymentViewModel.deleteRow(with: payment)
                                    } label: {
                                        Label("Удалить", systemImage: "trash.fill")
                                    }
                                    
                                    Button() {
                                        paymentViewModel.editFor(payment)
                                        isPresented.toggle()
                                    } label: {
                                        Label("Изменить", systemImage: "pencil.circle.fill")
                                    }
                                    .tint(.blue)
                                })
                        }
                    }
                }
            }
            .animation(.spring(), value: paymentViewModel.paymentsList)
            .onAppear {
                paymentViewModel.onViewAppear()
            }
            .sheet(isPresented: $isPresented, onDismiss: {
                paymentViewModel.onEditSheetDismissed()
            }, content: {
                EditingPaymentForm(paymentViewModel: paymentViewModel)
            })
            .navigationTitle("Платежи")
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button(action: {
                        isPresented.toggle()
                    }) {
                        Label("", systemImage: "note.text.badge.plus")
                    }
                }
            }
            .task {
                debugPrint(await paymentViewModel.getTest())
            }
        }
    }
}

struct PaymentsList_Previews: PreviewProvider {
    static var previews: some View {
        PaymentsList()
    }
}
