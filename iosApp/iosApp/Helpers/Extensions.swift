//
//  Extensions.swift
//  iosApp
//
//  Created by Данил Ломаев on 20.06.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import PaymentKit

extension Int32 {
    var asKotlinInt: KotlinInt  {
        KotlinInt(int: self)
    }
}

extension PaymentModel: Identifiable {}
