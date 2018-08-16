package service

import authentikat.jwt.{JsonWebToken, JwtClaimsSet, JwtHeader}
import com.google.inject.{Inject, Singleton}
import play.api.Configuration

@Singleton
class AuthService @Inject()(config: Configuration) {
  final val JwtSecretKey: String = config.get[String]("jwt.secret.key")
  final val JwtSecretAlgorithm: String = config.get[String]("jwt.secret.algo")

  def createToken(payload: String): String = {
    val header = JwtHeader(JwtSecretAlgorithm)
    val claimsSet = JwtClaimsSet(payload)
    JsonWebToken(header, claimsSet, JwtSecretKey)
  }

  def isValidToken(jwtToken: String): Boolean = JsonWebToken.validate(jwtToken, JwtSecretKey)

  def decodePayload(jwtToken: String): Option[String] = jwtToken match {
    case JsonWebToken(_, claimsSet, _) => Option(claimsSet.asJsonString)
    case _ => None
  }
}
