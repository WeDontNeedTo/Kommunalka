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
                    SumPayCard(sum: $paymentViewModel.sumForPay)
                }
                if paymentViewModel.isLoading {
                    ProgressView()
                } else {
                Section {
                        if paymentViewModel.paymentsList.isEmpty {
                            Text("Нету плажетей")
                                .font(.headline)
                        } else {
                            ForEach(paymentViewModel.paymentsList, id: \.self.id) { payment in
                                PaymentRow(payment: payment)
                                    .swipeActions(edge: .trailing, allowsFullSwipe: false, content: {
                                        Button(role: .destructive) {
                                            Task {
                                                await paymentViewModel.deleteRow(with: payment)
                                            }
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
            }
            .animation(.spring().speed(0.4), value: paymentViewModel.paymentsList)
            .task {
                await paymentViewModel.fetchPaymentList()
            }
            .refreshable {
                await paymentViewModel.fetchPaymentList(forceReload: true)
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
        }
    }
}

struct PaymentsList_Previews: PreviewProvider {
    static var previews: some View {
        PaymentsList()
    }
}
