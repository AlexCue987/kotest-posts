## Using Kotest to Replace Mocks with Test Doubles

### When Our Dependencies Are Functions, We Need Test Doubles, Not Mocks.

Generally, when we do functional programming, and our dependencies are functions, not objects, then when we test our code, we need less mocking, if any at all. And Kotest 6.0 has a couple of nice functions that come in very handy, helping us to build test doubles with less effort.
<br/>
<br/>

#### When Mocks Come in Very Handy

Consider, for example, the following object-oriented SpringBoot code:

```kotlin
@Service
class AnsweringService {
   fun answer(question: String): Int { TODO() }
}

@Service
class DecisionEngine(private val answeringService: AnsweringService) {
   fun answer(question: String): Int = answeringService.answer(question)
}
```

Naturally, when we unit test `DecisionEngine`, we should mock its dependency, an instance of `AnsweringService`, as follows:

```kotlin
val mockAnsweringService = run {
  val ret = mockk<AnsweringService>()
  every { ret.answer(any()) } returns 42
  ret
}

val myService = MyService(mockAnsweringService)

// tests to follow
```

Personally, I like using `mockk` library a lot. It is easy to learn, easy to use, and it gets the job done for me. It solves some very difficult problems for me, and it's an amzing feat of engineering. And of course whenever I'm building systems with SpringBoot, I'm using `mockk` day in and day out.
<br/>
<br/>
But when I'm using a more functional approach, when my dependencies are functions, and I'm wiring them up myself, I just don't need mocks - test-doubles get the job done with less effort. Let's see how it works.
<br/>
<br/>
Let's refactor the same code from the example above to be more functional, and see for ourselves how mocking becomes unnecessary. Here is the code refactored to be more functional

```kotlin
fun interface HasAnswer {
   fun answer(question: String): Int
}

class AnsweringService: HasAnswer {
   override fun answer(question: String): Int { TODO() }
}


class DecisionEngine(private val hasAnswer: HasAnswer) {
   fun answer(question: String): Int = hasAnswer.answer(question)
}
```
<br/>
<br/>
 And instead of standing up a mock, we can use a very simple test double, like this, which is so very much easier:

```kotlin
val myService = MyService(hasAnswer = { 42 })

// tests to follow
```
