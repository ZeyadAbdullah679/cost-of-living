package interactor

import model.CityEntity


class Consumption (private val dataSource: CostOfLivingDataSource){

    fun execute(): String {
         val x:MutableMap<Float,MutableList<String>> = mutableMapOf(Pair((0).toFloat(), mutableListOf()))
         val cities=dataSource
            .getAllCitiesData()
            .filter(::excludeNullValue)
            .sortedBy{it.realEstatesPrices.apartment3BedroomsOutsideOfCentre}
        cities.forEach {
             val servicesCoast= servicesCoast(it)
             val remainder= remainder(it,servicesCoast)
             if(isSuitable(servicesCoast,remainder)){
                 if (x.contains(remainder)) x[remainder]?.add(it.cityName)
                 else x[remainder] = mutableListOf(it.cityName)
             }
         }
        return x.toSortedMap()
            .values
            .last()
            .toString()
    }
}
private fun excludeNullValue(city: CityEntity): Boolean {
    return city.averageMonthlyNetSalaryAfterTax != null
            && city.dataQuality
            &&city.realEstatesPrices.apartment3BedroomsOutsideOfCentre != null
            &&city.foodPrices.riceWhite1kg != null
            &&city.foodPrices.chickenFillets1kg != null
            &&city.foodPrices.beefRound1kgOrEquivalentBackLegRedMeat != null
            &&city.foodPrices.localCheese1kg != null
            &&city.foodPrices.loafOfFreshWhiteBread500g != null
            &&city.servicesPrices.basicElectricityHeatingCoolingWaterGarbageFor85m2Apartment != null
            &&city.servicesPrices.internet60MbpsOrMoreUnlimitedDataCableAdsl != null
            &&city.servicesPrices.internationalPrimarySchoolYearlyForOneChild != null
            &&city.servicesPrices.oneMinOfPrepaidMobileTariffLocalNoDiscountsOrPlans != null
}

private fun servicesCoast(city:CityEntity):Float{
    return (city.servicesPrices.basicElectricityHeatingCoolingWaterGarbageFor85m2Apartment!!
        +(city.servicesPrices.internet60MbpsOrMoreUnlimitedDataCableAdsl!!)
        +(city.servicesPrices.internationalPrimarySchoolYearlyForOneChild!!/6)
        +(city.servicesPrices.oneMinOfPrepaidMobileTariffLocalNoDiscountsOrPlans!!))
    }
private fun remainder(city:CityEntity,servicesCoast:Float):Float{

    return( city.averageMonthlyNetSalaryAfterTax!!*2
            -city.realEstatesPrices.apartment3BedroomsOutsideOfCentre!!
            -city.foodPrices.riceWhite1kg!!*2
            -city.foodPrices.chickenFillets1kg!!*10
            -city.foodPrices.beefRound1kgOrEquivalentBackLegRedMeat!!*4
            -city.foodPrices.localCheese1kg!!
            -city.foodPrices.loafOfFreshWhiteBread500g!!*30
            -servicesCoast)
    }
private fun isSuitable(servicesCoast:Float,remainder:Float):Boolean{
    return servicesCoast <(250).toFloat() && remainder>(0).toFloat()
}