import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class AssertSoftlyTest: StringSpec() {
   private val myService = run {
      val ret = mockk<MyService>()
      every { ret.getAnswer(1, 2) } returns 3
      ret
   }

   init {
      "when verify fails, test fails" {
         2*2 shouldBe 4
         verify(exactly = 1) { myService.getAnswer(any(), any()) }
         //Verification failed: call 1 of 1: MyService(#1).getAnswer(any(), any())) was not called
      }
      "when verify fails, it throws an AssertionError" {
         val thrown = shouldThrowAny {
            verify(exactly = 1) { myService.getAnswer(1, 2) }
         }
         thrown.shouldBeInstanceOf<AssertionError>()
      }
      "assertSoftly works with most of kotest's assertions" {
         assertSoftly {
            2*2 shouldBe 5
            2+2 shouldBe 3
         }
         /*
The following 2 assertions failed:
1) expected:<5> but was:<4>
   at ...
2) expected:<3> but was:<4>
   at ...
          */
      }
      "but assertSoftly doesn't work with verify - second assertion won't run" {
         assertSoftly {
            verify(exactly = 1) { myService.getAnswer(1, 2) }
            2*2 shouldBe 5
         }
         //Verification failed: call 1 of 1: MyService(#1).getAnswer(eq(1), eq(2))) was not called
      }
      "and assertSoftly doesn't work with verify - verify's failure is ignored" {
         assertSoftly {
            2*2 shouldBe 5
            verify(exactly = 1) { myService.getAnswer(1, 2) }
         }
         //io.kotest.assertions.AssertionFailedError: expected:<5> but was:<4>
      }
      "Since Kotest 6, wrap shouldNotThrowAny around verify as first assertion" {
         assertSoftly {
            shouldNotThrowAny {
               verify(exactly = 1) { myService.getAnswer(1, 2) }
            }
            2*2 shouldBe 5
         }
         /*
The following 2 assertions failed:
1) No exception expected, but a AssertionError was thrown with message: "Verification failed: call 1 of 1: MyService(#1).getAnswer(eq(1), eq(2))) was not called".
2) expected:<5> but was:<4>
          */
      }
      "Since Kotest 6, wrap shouldNotThrowAny around verify as second assertion" {
         assertSoftly {
            2*2 shouldBe 5
            shouldNotThrowAny {
               verify(exactly = 1) { myService.getAnswer(1, 2) }
            }
         }
         /*
The following 2 assertions failed:
1) expected:<5> but was:<4>
2) No exception expected, but a AssertionError was thrown with message: "Verification failed: call 1 of 1: MyService(#1).getAnswer(eq(1), eq(2))) was not called".
          */
      }

   }

   class MyService {
      fun getAnswer(a: Int, b: Int): Int {
         println("a: $a, b: $b")
         TODO()
      }
   }
}

