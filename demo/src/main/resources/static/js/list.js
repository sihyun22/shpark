    async function checkBadWordsRealtime() {
      const title = document.getElementById('title')?.value || '';
      const writer = document.getElementById('writer')?.value || '';
      const content = document.getElementById('content')?.value || '';

      const response = await fetch('/posts/checkBadWords', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ title, writer, content })
      });

      const data = await response.json();

      document.getElementById('titleError').textContent = data.errors.title || '';
      document.getElementById('writerError').textContent = data.errors.writer || '';
      document.getElementById('contentError').textContent = data.errors.content || '';
      document.getElementById('submitBtn').disabled = data.hasBadWord;
    }