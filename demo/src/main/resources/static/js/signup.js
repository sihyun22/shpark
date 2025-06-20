document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('checkEmailBtn').addEventListener('click', function () {
        const uEmail = document.getElementById('username').value.trim();

        if (!uEmail) {
            alert('이메일을 입력해주세요.');
            return;
        }

        fetch(`/check-email?uEmail=${encodeURIComponent(uEmail)}`)
            .then(response => response.json())
            .then(data => {
                const resultDiv = document.getElementById('emailCheckResult');
                if (data.duplicate) {
                    resultDiv.style.color = 'red';
                    checkEmailBtn.textContent = '중복 확인 ❌'; 
                    checkEmailBtn.classList.remove('btn-dark', 'btn-success');
                    checkEmailBtn.classList.add('btn-danger');
                } else {
                    resultDiv.style.color = 'green';
                    checkEmailBtn.textContent = '중복 확인 ✅'; 
                    checkEmailBtn.classList.remove('btn-dark', 'btn-danger');
                    checkEmailBtn.classList.add('btn-success');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('서버와 통신 중 오류가 발생했습니다.');
            });
    });
});