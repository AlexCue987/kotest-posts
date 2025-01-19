## Assertions from other libraries are mostly compatible with Kotest, but not completely.

Generally, we can invoke non-kotest assertions inside kotest tests, and they work as expected, but not if we invoke them inside `assertSoftly`. 
<br/>
<br/>
For instance, even though `verify` fails in the following example, we'd expect `2*2 shouldBe 5` to evaluate and output failure message, but there is only one failure message in the output:

```kotest
 assertSoftly {
    verify(exactly = 1) { myService.getAnswer(1, 2) }
    2*2 shouldBe 5
 }

 Verification failed: call 1 of 1: MyService(#1).getAnswer(eq(1), eq(2))) was not called
```

Likewise, souppose that in the following example `verify` would fail if it were invoked on its own. We'd still expect it's error message in the output of `assertSoftly` block, but that is not happening:

```kotlin
 assertSoftly {
      2*2 shouldBe 5
      verify(exactly = 1) { myService.getAnswer(1, 2) }
   }

   io.kotest.assertions.AssertionFailedError: expected:<5> but was:<4>
```

### `verify` fails a test, as expected
