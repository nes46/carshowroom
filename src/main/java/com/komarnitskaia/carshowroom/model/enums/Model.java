package com.komarnitskaia.carshowroom.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum Model {
    S_KLASS(Brand.MERCEDES),
    E_KLASS(Brand.MERCEDES),
    C_KLASS(Brand.MERCEDES),

    SERIES_5(Brand.BMW),
    SERIES_7(Brand.BMW),
    X5(Brand.BMW),

    Q7(Brand.AUDI),
    A4(Brand.AUDI),
    TT(Brand.AUDI);

    private final Brand brand;

    public static List<Model> getModelsByBrand(Brand brand) {
        List<Model> result = new ArrayList<>();
        for (Model model : Model.values()) {
            if (brand.equals(model.getBrand())) {
                result.add(model);
            }
        }
        return result;
    }
}
