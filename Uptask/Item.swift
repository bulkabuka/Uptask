//
//  Item.swift
//  Uptask
//
//  Created by Erich Helvig on 16.10.2023.
//

import Foundation
import SwiftData

@Model
final class Item {
    var timestamp: Date
    
    init(timestamp: Date) {
        self.timestamp = timestamp
    }
}
