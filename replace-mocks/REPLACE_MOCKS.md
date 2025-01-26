## Using Kotest to Replace Mocks with Test Doubles

### When Our Dependencies Are Functions, We Need Test Doubles, Not Mocks.

Generally, when we do functional programming, and our dependencies are functions, not objects, then when we test our code, we need less mocking, if any at all.
<br/>
<br/>
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

Personally, I like using `mockk` library a lot. It is easy to learn, easy to use, and it gets the job done for me. When I'm building systems with SpringBoot, I'm using `mockk` day in and day out.
<br/>
<br/>
But when I'm using a more functional approach and wiring up my dependencies myself, let's refactor the same code to be more functional, and mocking becomes unnecessary.

Personally, while my previous project was Kotlin and SpringBoot, my current gig is completely framework-free. So I'm wiring up my dependencies myself, and know what? Even though the code is covered with tests really well, I have never felt the need to use mocks - when my dependencies are functions, test-double functions get the job done with less effort.
<br/>
<br/>
 
