package cfgeng

class LConfigToken(var tokenType: LConfigTokenType.LConfigTokenType, var tokenValue: String) {

  def print() = printf("Token found '%s' with value '%s'\n", tokenType.toString, tokenValue)
}

object LConfigToken {

  val None = new LConfigToken(LConfigTokenType.TokenNone, "")

}