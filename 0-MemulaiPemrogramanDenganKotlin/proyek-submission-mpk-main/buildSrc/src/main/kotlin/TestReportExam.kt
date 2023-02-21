import com.google.gson.GsonBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.w3c.dom.Element
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

data class Result (val isPassed: Boolean = false, val exam: String? = null, val testcaseName: String? = null, val errorMessage: String? = null)
data class FinalResult(val allExamPassed: Boolean, val exam: String?, val errors: List<String> = listOf())

abstract class TestReportExam : DefaultTask() {
    @TaskAction
    fun run() {

        val file = "${project.buildDir}/test-results/test/TEST-ExamTest.xml"
        if (!File(file).isFile) {
            return println("Test report no found")
        }

        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val doc = builder.parse(File(file))

        doc.normalizeDocument()

        val results = mutableListOf<Result>()
        val testcaseNodes = doc.getElementsByTagName("testcase")

        repeat(testcaseNodes.length) {
            val testsuite = testcaseNodes.item(it) as Element
            val failure = testsuite.getElementsByTagName("failure").item(0) as? Element
            val testcaseName = testsuite.getAttribute("name")
            val exam = testcaseName.split("--").first().trim()
            val readableTestcaseName = testcaseName.replace("$exam --", "").trim()

            if (failure !== null) {
                val errorMessage = failure.getAttribute("message").split("AssertionFailedError: ").last()
                results.add(Result(false, exam, readableTestcaseName, errorMessage))
            } else {
                results.add(Result(true, exam, readableTestcaseName))
            }
        }

        val finalResult = mutableListOf<FinalResult>()
        val resultsGroupByExam = results.groupBy { it.exam }.values
        resultsGroupByExam.forEach { eachResult ->
            val failedTest = eachResult.filter { !it.isPassed }
            if (failedTest.isEmpty()) {
                finalResult.add(FinalResult(true, eachResult.first().exam))
                colored(enabled = true) {
                    println("\n ${eachResult.first().exam} - ALL SUCCESS ".green.bold)
                }
                return@forEach
            }

            val errors = mutableListOf<String>()
            val mutableFailedTest = failedTest.toMutableList()
            mutableFailedTest.add(0, Result(exam = failedTest.first().exam))
            mutableFailedTest.forEachIndexed { index, result ->
                if (index == 0) {
                    colored(enabled = true) {
                        println("\n ${result.exam} - ERROR  ".red.bold )
                    }
                } else {
                    errors.add(result.errorMessage ?: "")
                    colored(enabled = true) {
                        println(" > ${result.errorMessage} " )
                    }
                }
            }
            finalResult.add(FinalResult(false, eachResult.first().exam, errors))
        }

        val result = GsonBuilder().setPrettyPrinting().create()
        File(project.projectDir.toString() +  "/result.json").writeText(result.toJson(finalResult))
    }
} 