package interactor

import model.CityEntity

private var temp=0.toFloat()
private var destination:CityEntity?=null
class Consumption (private val dataSource: CostOfLivingDataSource){

    val execute ={
         this.dataSource
            .getAllCitiesData()
            .forEach { check(it) }
        destination
    }
}
private fun check(city: CityEntity){
    if (!isThereNullValue(city))
    {val remainder= remainder(city)
        if(remainder>(250).toFloat()){
            if(remainder> temp){
                temp=remainder
                destination=city
            }
        }
    }
}
private fun isThereNullValue(city: CityEntity): Boolean {
    return (city.averageMonthlyNetSalaryAfterTax == null
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