const prevBtns = document.querySelectorAll(".btn-prev");
const nextBtns = document.querySelectorAll(".btn-next");
const progress = document.getElementById("progress");
const formSteps = document.querySelectorAll(".form-step");
const progressSteps = document.querySelectorAll(".progress-step");

let formStepsNum = 0;

nextBtns.forEach((btn) => {
  btn.addEventListener("click", () => {
    if (formStepsNum === 1 && !allQuestionsAnswered()) {
      return;
    }
    formStepsNum++;
    updateFormSteps();
    updateProgressbar();
  });
});

prevBtns.forEach((btn) => {
  btn.addEventListener("click", () => {
    formStepsNum--;
    updateFormSteps();
    updateProgressbar();
  });
});

function updateFormSteps() {
  formSteps.forEach((formStep) => {
    formStep.classList.remove("form-step-active");
  });

  formSteps[formStepsNum].classList.add("form-step-active");
  toggleNextButton();
  updateProgressbar();
}


function updateProgressbar() {
  progressSteps.forEach((progressStep, idx) => {
    if (idx < formStepsNum + 1) {
      progressStep.classList.add("progress-step-active");
    } else {
      progressStep.classList.remove("progress-step-active");
    }
  });

  const progressActive = document.querySelectorAll(".progress-step-active");
  progress.style.width =
      ((progressActive.length - 1) / (progressSteps.length - 1)) * 100 + "%";
}

function toggleNextButton() {
  const nextButton = nextBtns[formStepsNum];
  if (!nextButton) return;

  if (formStepsNum === 1) {
    const allAnswered = allQuestionsAnswered();
    nextButton.disabled = !allAnswered;
    nextButton.classList.toggle('disabled', !allAnswered);
  } else {
    nextButton.disabled = false;
    nextButton.classList.remove('disabled');
  }
}

function allQuestionsAnswered() {
  const currentStep = formSteps[formStepsNum];
  const questions = currentStep.querySelectorAll('.domanda');

  for (const question of questions) {
    const inputGroup = question.nextElementSibling;

    if (!inputGroup || !inputGroup.classList.contains('input-group')) {
      console.error('Input group non trovato per la domanda:', question);
      return false;
    }

    const radios = inputGroup.querySelectorAll('input[type="radio"]');
    const isAnswered = Array.from(radios).some(radio => radio.checked);

    if (!isAnswered) {
      return false;
    }
  }
  return true;
}

document.querySelectorAll('.input-group input[type="radio"]').forEach((radio) => {
  radio.addEventListener('change', toggleNextButton);
});
