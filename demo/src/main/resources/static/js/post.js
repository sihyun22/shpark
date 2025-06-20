document.querySelectorAll('.edit-btn').forEach(button => {
  button.addEventListener('click', () => {
    const id = button.value; 
    const title = button.getAttribute('data-title');
    const content = button.getAttribute('data-content');
    const writer = button.getAttribute('data-writer');

    document.getElementById('modal-post-id').value = id;
    document.getElementById('title').value = title;
    document.getElementById('content').value = content;
    document.getElementById('writer').value = writer;

    const editModal = new bootstrap.Modal(document.getElementById('editModal'));
    editModal.show();
  });
});

document.querySelectorAll('.delete-btn').forEach(button => {
  button.addEventListener('click', () => {
    const id = button.value;

    document.getElementById('modal-post-id-del').value = id;

    const deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
    deleteModal.show();
  });
});

document.querySelectorAll('.resetBtn').forEach(button => {
	button.addEventListener('click', () => {
		    window.location.href = '/list'; 
	})
})