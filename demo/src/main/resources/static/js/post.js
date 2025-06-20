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

console.log("이게보이니?")

document.addEventListener("DOMContentLoaded", function () {
  const notices = document.querySelectorAll(".card .list-group-item[data-start]");
  const now = new Date();

  let shouldOpen = false;

  console.log("notices length:", notices.length);

  notices.forEach((item) => {
    console.log("data-start:", item.dataset.start);
    console.log("data-end:", item.dataset.end);

    const start = new Date(item.dataset.start);
    const end = new Date(item.dataset.end);

    console.log("Parsed start:", start);
    console.log("Parsed end:", end);

    if (now >= start && now <= end) {
      shouldOpen = true;
    }
  });

  if (shouldOpen) {
    const modalElement = document.getElementById("noticeModal");
    if (modalElement) {
      const modal = new bootstrap.Modal(modalElement);
      modal.show();
      console.log("공지 모달 오픈");
    } else {
      console.log("모달 요소를 찾을 수 없습니다.");
    }
  } else {
    console.log("현재 시간에는 표시할 공지 없음");
  }
});