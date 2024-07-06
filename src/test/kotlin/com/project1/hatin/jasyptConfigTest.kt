import org.assertj.core.api.Assertions.assertThat
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import org.junit.jupiter.api.Test

class JasyptConfigTest {

    @Test
    fun jasypt() {
        val url = "jdbc:mysql://localhost:3306/hatin?autoReconnect=true&useUnicode=true&serverTimezone=Asia/Seoul"
        val username = "project1"
        val password = "1234"

        val encryptUrl = jasyptEncrypt(url)
        val encryptUsername = jasyptEncrypt(username)
        val encryptPassword = jasyptEncrypt(password)

        println("encryptUrl : $encryptUrl")
        println("encryptUsername : $encryptUsername")
        println("encryptPassword : $encryptPassword")

        assertThat(url).isEqualTo(jasyptDecrypt(encryptUrl))
    }

    private fun jasyptEncrypt(input: String): String {
        val key = "5678"
        val encryptor = StandardPBEStringEncryptor()
        encryptor.setAlgorithm("PBEWithMD5AndDES")
        encryptor.setPassword(key)
        return encryptor.encrypt(input)
    }

    private fun jasyptDecrypt(input: String): String {
        val key = "5678"
        val encryptor = StandardPBEStringEncryptor()
        encryptor.setAlgorithm("PBEWithMD5AndDES")
        encryptor.setPassword(key)
        return encryptor.decrypt(input)
    }
}
