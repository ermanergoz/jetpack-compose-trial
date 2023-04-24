package com.example.myapplication.model

import com.kole.myapplication.cms.nnsettings.NNSettingsString

enum class ProductAttribute(var id: String, var attributeName: String) {
    BRAND_WITH_PURPOSE(
        "isBrandWithPurpose", NNSettingsString("BrandWithPurposeAttr")
    ),
    PETA_ACCREDITED(
        "isPetaLeapingBunnyAccredited", NNSettingsString("PetaAttr")
    );

    companion object {
        fun getAttributeById(id: String): ProductAttribute? {
            for (attribute in values()) {
                if (attribute.id == id) {
                    return attribute
                }
            }
            return null
        }
    }
}
