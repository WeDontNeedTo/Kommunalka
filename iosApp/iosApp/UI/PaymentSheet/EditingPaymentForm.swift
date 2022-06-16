//
//  AddPaymentView.swift
//  Kommunalka
//
//  Created by Данил Ломаев on 28.05.2022.
//

import SwiftUI

struct EditingPaymentForm: View {
    @ObservedObject var paymentViewModel: PaymentViewModel
    @Environment(\.presentationMode) var presentationMode
    @State private var hotWaterText: String = ""
    @State private var coldWaterText: String = ""
    @State private var electricityText: String = ""
    
    private var isValidForm: Bool {
        (paymentViewModel.editingPayment.hotWaterCount > 0) && (paymentViewModel.editingPayment.coldWaterCount > 0) && (paymentViewModel.editingPayment.electricity > 0) && (!paymentViewModel.editingPayment.date.isEmpty)
    }
    
    private var dateFormatter: DateFormatter {
        let df = DateFormatter()
        df.dateStyle = .short
        return df
    }

    var body: some View {
        NavigationView {
            Form {
                DatePicker("Дата", selection: $paymentViewModel.selectedDate, displayedComponents: .date)
                    .datePickerStyle(.compact)
                    .onChange(of: paymentViewModel.selectedDate) { newValue in
                        self.paymentViewModel.editingPayment.date = dateFormatter.string(from: newValue)
                    }
                
                VStack(alignment: .leading, spacing: 24) {
                    Section {
                        VStack(alignment: .leading, spacing: 8) {
                            HStack {
                                Label("Горячая вода: ", systemImage: "flame.circle")
                                Text("\(paymentViewModel.editingPayment.hotWaterCount.description) куб/м")
                            }
                            .font(.headline)
                            .foregroundColor(.red)
                            
                            TextField("Введите горячую воду", text: $hotWaterText)
                                .onChange(of: hotWaterText) { newValue in
                                    paymentViewModel.editingPayment.hotWaterCount = Int(newValue) ?? 0
                                }
                        }

                        VStack(alignment: .leading, spacing: 8) {
                            HStack {
                                Label("Холодная вода: ", systemImage: "humidity.fill")
                                Text("\(paymentViewModel.editingPayment.coldWaterCount.description) куб/м")
                            }
                            .font(.headline)
                            .foregroundColor(.cyan)
                            
                            TextField("Введите холодную воду", text: $coldWaterText)
                                .onChange(of: coldWaterText) { newValue in
                                    paymentViewModel.editingPayment.coldWaterCount = Int(newValue) ?? 0
                                }
                        }
                        
                        VStack(alignment: .leading, spacing: 8) {
                            HStack {
                                Label("Электроэнергия: ", systemImage: "bolt.square.fill")
                                Text("\(paymentViewModel.editingPayment.electricity.description) кВт/ч")
                            }
                            .font(.headline)
                            .foregroundColor(.orange)
                            
                            TextField("Введите электричество", text: $electricityText)
                                .onChange(of: electricityText) { newValue in
                                    paymentViewModel.editingPayment.electricity = Int(newValue) ?? 0
                                }
                        }
                    }
                    
                    Section {
                        Button(action: {
                            paymentViewModel.onPaymentDoneButtonPressed()
                            presentationMode.wrappedValue.dismiss()
                        }) {
                            Text(paymentViewModel.isEditing ? "Изменить" : "Добавить")
                        }
                        .buttonStyle(.borderedProminent)
                        .disabled(!isValidForm)
                        .opacity(isValidForm ? 1.0 : 0.8)
                    }
                }
                .padding(.vertical)
            }
            .navigationTitle("Запись")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button(action: {
                        presentationMode.wrappedValue.dismiss()
                    }) {
                        Text("Закрыть")
                    }
                }
            }
            .onAppear {
                paymentViewModel.calculateDateField(with: dateFormatter)
            }
        }
    }
}

struct AddPaymentView_Previews: PreviewProvider {
    static var previews: some View {
        EditingPaymentForm(
            paymentViewModel: PaymentViewModel(paymentRepository: PaymentRepository())
        )
    }
}
