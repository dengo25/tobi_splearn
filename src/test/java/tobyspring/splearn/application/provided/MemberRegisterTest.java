package tobyspring.splearn.application.provided;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import tobyspring.splearn.SplearnTestConfiguration;
import tobyspring.splearn.domain.DuplicateEmailException;
import tobyspring.splearn.domain.Member;
import tobyspring.splearn.domain.MemberFixture;
import tobyspring.splearn.domain.MemberStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL) //spring container를 통해서 설정을 갖고온다. -> test-properties
public record MemberRegisterTest(MemberRegister memberRegister) {
  
  @Test
  void register() {
    Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
    
    assertThat(member.getId()).isNotNull();
    assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    
  }
  
  @Test
  void duplicateEmailFail() {
    Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
    
    assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRegisterRequest()))
        .isInstanceOf(DuplicateEmailException.class);
    
  }
  
}
