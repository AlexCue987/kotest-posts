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

Naturally, when we unit test `DecisionEngine`, we should mock its dependency, `answeringService`, as follows:

```kotlin
val mockAnsweringService = run {
  val ret = mockk<AnsweringService>()
  every { ret.answer(any()) } returns 42
  ret
}

val myService = MyService(mockAnsweringService)
```
Personally, while my previous project was Kotlin and SpringBoot, my current gig is completely framework-free. So I'm wiring up my dependencies myself, and know what? Even though the code is covered with tests really well, I have never felt the need to use mocks - when my dependencies are functions, test-double functions get the job done with less effort.
<br/>
<br/>
 
