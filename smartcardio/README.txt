This is a clone of the original javax.smartcardio intended to support PC/SC reader groups.

Design Rationale
The methods of which the behavior needs to be modified are
* CardTerminals.list()
* CardTerminals.list(CardTerminal.State)
These methods shall look for readers of a specific PC/SC reader group.
These methods do not take a parameter that could be used for that purpose.
Therefore, the change needs to be done elsewhere.
The change will be implemented in TerminalFactory.
The class TerminalFactory has getInstance() methods which allow passing parameters.

Changes made so far:
* Introduce standalone build system.
* Change LPSCARD_READERSTATE_A to LPSCARD_READERSTATE
