package cfgeng

class LConfigToken(var tokenType: LConfigTokenType.LConfigTokenType, var tokenValue: String, val startLine: Int = 0, val startChar: Int = 0, val endLine: Int = 0, val endChar: Int = 0) {

  def print() = printf("Token found '%s' with value '%s'\n", tokenType.toString, tokenValue)
}

object LConfigToken {

  val None = new LConfigToken(LConfigTokenType.TokenNone, "")

}