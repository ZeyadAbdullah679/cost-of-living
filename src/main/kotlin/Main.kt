import dataSource.CsvDataSource
import dataSource.utils.CsvParser
import interactor.Consumption
import interactor.CostOfLivingDataSource
fun main() {
    val csvParser = CsvParser()
    val dataSource: CostOfLivingDataSource = CsvDataSource(csvParser)

    print(Consumption(dataSource).execute())
    printSeparationLine()
}
private fun printSeparationLine(){
    print("\n_______________________________\n")
}

