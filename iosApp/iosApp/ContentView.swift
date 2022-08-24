import SwiftUI
import PaymentKit

struct ContentView: View {
    @State private var isShowedIntro: Bool = false
    
	var body: some View {
        ZStack {
            if isShowedIntro {
                PaymentsList()
                    .transition(.identity)
            } else {
                IntroView(isShowedIntro: $isShowedIntro)
                    .transition(.identity)
            }
        }
        .animation(.spring(), value: isShowedIntro)
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
