### Mobile developer test for ML

## Goal
Develop an application that complies with the following demands

- Amount input screen
- Payment method selection screen
- Issuer selection screen, based on payment method selected screen
- Installment selection screen, that depends on both payment method and issuer selected.
- Finally after the user selects all the options show in the home screen (amount input screen) all the options selected.


## Comments
- About development language

I decided to implement the application using Kotlin programming language as part of the challenge because there are not many spaces where you can do it.
 
- About libs:

As part of the libs selected on this project I selected the new architecture components made by google, retrofit, RX java, RX android and dagger2 for dependency injection

- About dependency injection:

In this case, the app was simple and I decided to implement just a dependency context based on Application.

- About persistence:

There is an implementation for persistence with a database, is it driven by the new android lib ROOM, but also there is
a 10 mb cache added to the network client. If the backend send RFC cache headers the client will handle the request as demanded.

- About images:

For icon display (depending on version) I added some vector drawables made with SVG files.

- About network module:

I did not follow the rule about modularize the calls as dynamic parameters but if you want
to do it is easy to accomplish adding a class abstraction for that and using @path and @query (retrofit)
with a map of values.

- About unit tests / integration tests:

I decided not to implement any test for this project, but if you want me to added feel free to ask.

- About UX / UI:

There are no loading screens implemented or error handling for clients based on the requirements.
The same way as tests, ask me if you need them.