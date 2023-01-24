package datasource.service

import datasource.core.http.HttpClientFactory
import datasource.dummy.BreedJsonDummy
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockEngineConfig
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals
import org.junit.Test
import kotlin.test.assertFails

@ExperimentalCoroutinesApi
internal class BreedsHttpService_FetchBreedsNamesTest {

    private val expectedResult = listOf("affenpinscher","african","airedale","akita","appenzeller",
        "shepherd australian","basenji","beagle","bluetick","borzoi","bouvier","boxer","brabancon",
        "briard","norwegian buhund","boston bulldog","english bulldog","french bulldog",
        "staffordshire bullterrier","australian cattledog","chihuahua","chow","clumber","cockapoo"
        ,"border collie","coonhound","cardigan corgi","cotondetulear","dachshund","dalmatian",
        "great dane","scottish deerhound","dhole","dingo","doberman","norwegian elkhound",
        "entlebucher","eskimo","lapphund finnish","bichon frise","germanshepherd","italian greyhound"
        ,"groenendael","havanese","afghan hound","basset hound","blood hound","english hound"
        ,"ibizan hound","plott hound","walker hound","husky","keeshond","kelpie","komondor","kuvasz"
        ,"labradoodle","labrador","leonberg","lhasa","malamute","malinois","maltese","bull mastiff"
        ,"english mastiff","tibetan mastiff","mexicanhairless","mix","bernese mountain",
        "swiss mountain","newfoundland","otterhound","caucasian ovcharka","papillon","pekinese"
        ,"pembroke","miniature pinscher","pitbull","german pointer","germanlonghair pointer",
        "pomeranian","medium poodle","miniature poodle","standard poodle","toy poodle","pug",
        "puggle","pyrenees","redbone","chesapeake retriever","curly retriever","flatcoated retriever"
        ,"golden retriever","rhodesian ridgeback","rottweiler","saluki","samoyed","schipperke",
        "giant schnauzer","miniature schnauzer","italian segugio","english setter","gordon setter"
        ,"irish setter","sharpei","english sheepdog","shetland sheepdog","shiba","shihtzu",
        "blenheim spaniel","brittany spaniel","cocker spaniel","irish spaniel","japanese spaniel"
        ,"sussex spaniel","welsh spaniel","japanese spitz","english springer","stbernard",
        "american terrier","australian terrier","bedlington terrier","border terrier"
        ,"cairn terrier","dandie terrier","fox terrier","irish terrier","kerryblue terrier"
        ,"lakeland terrier","norfolk terrier","norwich terrier","patterdale terrier"
        ,"russell terrier","scottish terrier","sealyham terrier","silky terrier","tibetan terrier"
        ,"toy terrier","welsh terrier","westhighland terrier","wheaten terrier","yorkshire terrier"
        ,"tervuren","vizsla","spanish waterdog","weimaraner","whippet","irish wolfhound"
    )

    @Test
    fun `should return breeds string list when given good response`() = runTest {
        val engine = MockEngine(
            MockEngineConfig().apply {
                addHandler {
                    respond(
                        content = BreedJsonDummy.SUCCESS_DATA,
                        headers = headersOf("Content-Type" to listOf("application/json")),
                        status = HttpStatusCode.OK
                    )
                }
            }
        )
        val client = HttpClientFactory(engine).build()
        val service = BreedsHttpService("localhost", client)

        // Act
        val actualResult = service.fetchBreedsNames()

        // Assert
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `should throws on invalid json response`() = runTest {
        val engine = MockEngine(
            MockEngineConfig().apply {
                addHandler {
                    respond(
                        content = "Not Valid Json",
                        headers = headersOf("Content-Type" to listOf("application/json")),
                        status = HttpStatusCode.OK
                    )
                }
            }
        )
        val client = HttpClientFactory(engine).build()
        val service = BreedsHttpService("localhost", client)

        // Assert
        assertFails("Should throw on invalid json response") {
            service.fetchBreedsNames() // Act
        }
    }

    @Test
    fun `should throws on invalid on status code == 500`() = runTest {
        val engine = MockEngine(
            MockEngineConfig().apply {
                addHandler {
                    respond(
                        content = BreedJsonDummy.SUCCESS_DATA,
                        headers = headersOf("Content-Type" to listOf("application/json")),
                        status = HttpStatusCode.InternalServerError
                    )
                }
            }
        )
        val client = HttpClientFactory(engine).build()
        val service = BreedsHttpService("localhost", client)

        // Assert
        assertFails("Should throw on invalid json response") {
            service.fetchBreedsNames() // Act
        }
    }
}

