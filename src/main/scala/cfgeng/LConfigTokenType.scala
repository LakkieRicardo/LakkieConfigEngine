package cfgeng

object LConfigTokenType extends Enumeration {
  type LConfigTokenType = Value
  val TokenNone, TokenString, TokenNumber, TokenIdentifier, TokenBraceOpen, TokenBraceClose, TokenAssignment = Value
}