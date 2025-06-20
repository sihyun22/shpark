async function checkBadWordsRealtime() {
    const tokenMeta = document.querySelector('meta[name="_csrf"]');
    const headerMeta = document.querySelector('meta[name="_csrf_header"]');

    if (!tokenMeta || !headerMeta) {
        console.error("CSRF meta tag not found!");
        return;
    }

    const token = tokenMeta.getAttribute('content');
    const header = headerMeta.getAttribute('content');

    const title = document.getElementById('title').value;
    const writer = document.getElementById('writer').value;
    const content = document.getElementById('content').value;

    const response = await fetch('/posts/checkBadWords', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [header]: token
        },
        body: JSON.stringify({ title, writer, content })
    });

    const data = await response.json();

    document.getElementById('titleError').textContent = data.errors.title || '';
    document.getElementById('writerError').textContent = data.errors.writer || '';
    document.getElementById('contentError').textContent = data.errors.content || '';

    document.getElementById('submitBtn').disabled = data.hasBadWord;
}