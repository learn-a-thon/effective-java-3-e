package com.example.effective.item22;

import static com.example.effective.item22.PhysicalConstantsClass.*;
public class Test {
    double atoms(double mols) {
        return AVOGADROS_NUMBER * mols; // static import
    }
}
