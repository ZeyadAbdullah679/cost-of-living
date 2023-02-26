package interactor

import model.CityEntity


class Consumption (private val dataSource: CostOfLivingDataSource){

    fun execute(): CityEntity {
         val x: MutableMap<Float,MutableList<CityEntity>> = mutableMapOf()
         this.dataSource
            .getAllCitiesData()
            .filter(::excludeNullOrZeroValue)
            .forEach {
             val remainder= remainder(it)
             if(remainder>(250).toFloat()){
                 if (x.contains(remainder)) x[remainder]?.add(it)
                 else x[remainder] = mutableListOf(it)
             }
         }
        return x.toSortedMap()
            .values
            .last()[0]
    }
}
private fun excludeNullOrZeroValue(city: CityEntity): Boolean {
    return !(city.averageMonthlyNetSalaryAfterTax == null
            || city.realEstatesPrices.apartment3BedroomsOutsideOfCentre == null
            || city.foodPrices.riceWhite1kg == null
            || city.foodPrices.chickenFillets1kg == null
            || city.foodPrices.beefRound1kgOrEquivalentBackLegRedMeat == null
            || city.foodPrices.localCheese1kg == null
            || city.foodPrices.loafOfFreshWhiteBread500g == null)
}
private fun remainder(city:CityEntity):Float{

    return( city.averageMonthlyNetSalaryAfterTax!!*2
            -city.realEstatesPrices.apartment3BedroomsOutsideOfCentre!!
            -city.foodPrices.riceWhite1kg!!*2
            -city.foodPrices.chickenFillets1kg!!*10
            -city.foodPrices.beefRound1kgOrEquivalentBackLegRedMeat!!*4
            -city.foodPrices.localCheese1kg!!
            -city.foodPrices.loafOfFreshWhiteBread500g!!*30)
    }