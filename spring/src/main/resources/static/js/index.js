// ################################################################################
// Global State
// ################################################################################
let Projects = [];
let IncompleteTasks = [];
let CompleteTasks = [];
let TaskSummary = [];

// ################################################################################
// Methods
// ################################################################################
$(async () => {
  switchFocusCSSByAddTasks();
  // 初期データ取得
  await fetchData();
  // 初期描画
  initView();

  setupFormSubmitHandler();

  $('#Task_Main_Add_Form_Priority .dropdown-item').on('click', (e) => {
    e.preventDefault(); // デフォルトのリンク動作をキャンセル
    const selectedValue = $(e.currentTarget).data('value'); // data-value 属性から値を取得
    $('#Task_Main_Add_Form_Priority input').val(selectedValue); // 隠しフィールドに値を設定
    $('#Task_Main_Add_Form_Priority_Button i').prop('class', $(e.currentTarget).find('i').attr('class'));
  });
});

const fetchData = async () => {
  Projects = await findAllProjects();

  const tasks = await findAllTasksByProjectId();
  IncompleteTasks = tasks.filter((task) => task.completedAt === null);
  CompleteTasks = tasks.filter((task) => task.completedAt !== null);
};

const initView = () => {
  viewProjectLists();
  initTask();
};

const initTask = () => {
  viewIncompleteTaskList();
  viewCompleteTaskList();
  viewTaskSummary();
  initTaskAccordion();
}

// プロジェクト一覧の取得/表示
const viewProjectLists = () => {
  // リセット
  $('#Task_LeftSideBar_Project').empty();
  // プロジェクト一覧の生成
  const defaultProjectElement = generateProjectElement();
  defaultProjectElement.closest('.row').addClass('selected');
  $('#Task_LeftSideBar_Project').append(defaultProjectElement);

  $.each(Projects, (_, project) => $('#Task_LeftSideBar_Project').append(generateProjectElement(project)));
};

const viewIncompleteTaskList = () => {
  // リセット
  $('#Task_Main_List_Incomplete_Contents .accordion-body').empty();

  IncompleteTasks.length
    ? $.each(IncompleteTasks, (_, task) => $('#Task_Main_List_Incomplete_Contents .accordion-body').append(generateTaskElement(task)))
    : $('#Task_Main_List_Incomplete_Contents .accordion-body').append($('<div>', {
      text: "未完了のタスクがありません",
    }));
};

const viewCompleteTaskList = () => {
  // リセット
  $('#Task_Main_List_Complete_Contents .accordion-body').empty();

  CompleteTasks.length
    ? $.each(CompleteTasks, (_, task) => $('#Task_Main_List_Complete_Contents .accordion-body').append(generateTaskElement(task, true)))
    : $('#Task_Main_List_Complete_Contents .accordion-body').append($('<div>', {
      text: '完了済みのタスクはありません'
    }));
};

const viewTaskSummary = () => {
  $('#Task_Main_Summary_IncompleteCount_Num').empty().append(IncompleteTasks.length);
  $('#Task_Main_Summary_CompleteCount_Num').empty().append(CompleteTasks.length);
};

const setupFormSubmitHandler = () => {
  onSubmitCreateProject();
  onSubmitCreateTag();
  onSubmitCreateTask();
};

const onSubmitCreateProject = () => {
  $('#AddProjectModal_Form').on('submit', (e) => {
    // 標準のフォーム送信をキャンセル
    e.preventDefault();

    createProject(convertFromFormElementToHash(e.currentTarget))
      .then((project) => Projects.push(project))
      .then(() => viewProjectLists())
      .then(() => e.currentTarget.reset())
      .then(() => $('#AddProjectModal').modal('hide'));
  });
};

const onSubmitCreateTag = () => {
  $('#AddTagModal_Form').on('submit', (e) => {
    // 標準のフォーム送信をキャンセル
    e.preventDefault();

    createTag(convertFromFormElementToHash(e.currentTarget))
      .then(() => e.currentTarget.reset())
      .then(() => $('#AddTagModal').modal('hide'));
  });
}

const onSubmitCreateTask = () => {
  $('#Task_Main_Add_Form').on('submit', (e) => {
    // 標準のフォーム送信をキャンセル
    e.preventDefault();

    const formParams = convertFromFormElementToHash(e.currentTarget);

    if (!formParams.name) {
      alert('必ずタスク名は入力してください');
      return;
    }

    const params = {
      ...formParams,
      projectId: $('#Task_LeftSideBar_Project .selected a').data('id'),
    };

    createTask(params)
      .then((task) => IncompleteTasks.unshift(task))
      .then(() => viewIncompleteTaskList())
      .then(() => viewTaskSummary())
      .then(() => e.currentTarget.reset())
  });
};

const convertFromFormElementToHash = (htmlFormElement) => Object.fromEntries(new FormData(htmlFormElement).entries())

const switchFocusCSSByAddTasks = () => {
  // タスク追加のフォーカスCSS切替
  $('#Task_Main_Add_Form .form-control')
    .on('focus', (e) => $(e.currentTarget).parent('.input-group').addClass('focused'))
    .on('blur', (e) => $(e.currentTarget).parent('.input-group').removeClass('focused'));
}

const generateProjectLinkButton = (project = undefined) => {
  return $('<a>', {
    text: project?.name ?? 'プロジェクト未指定', // リンクのテキスト
    href: '#', // ページ遷移させないために、`#` を設定
    class: 'link-button text-decoration-none text-body',
    data: { id: project?.id },
    on: {
      click: async (e) => {
        e.preventDefault();

        $('#Task_Main_Add_Form input').val('');
        initTaskAccordion();

        const projectId = $(e.currentTarget).data('id');
        const tasks = await findAllTasksByProjectId(projectId);

        IncompleteTasks = tasks.filter((task) => task.completedAt === null);
        CompleteTasks = tasks.filter((task) => task.completedAt !== null);

        initTask();
      },
      focus: (e) => {
        $('#Task_LeftSideBar_Project').find('.selected').removeClass('selected');
        $(e.currentTarget).closest('.row').addClass('selected');
      },
    }
  });
};

const initTaskAccordion = () => {
  $('#Task_Main_List_Complete_Contents').prop('class', 'accordion-collapse collapse');
  $('#Task_Main_List_Incomplete_Contents').prop('class', 'accordion-collapse collapse show');
  $('#Task_Main_List_Complete .accordion-button').prop('class', 'accordion-button collapsed');
  $('#Task_Main_List_Incomplete .accordion-button').prop('class', 'accordion-button');
};

const generateProjectElement = (project = undefined) => {
  const nameDiv = $('<div>').addClass('lh-lg');
  nameDiv.append(generateProjectLinkButton(project));

  const colDiv = $('<div>').addClass('col px-0 d-flex justify-content-between');
  colDiv.append(nameDiv);

  const rowDiv = $('<div>').addClass('row');
  rowDiv.append(colDiv);

  return rowDiv;
};

const priorityClassName = {
  LOW: 'bi bi-chevron-double-down text-primary',
  MEDIUM: 'bi bi-water text-warning',
  HIGH: 'bi bi-chevron-double-up text-danger',
};

const generateTaskElement = (task, is_completed = false) => {
  const icon = is_completed ? generateCompleteTaskCircleIconElement() : generateIncompleteTaskCircleIconElement(task);
  const leftContentDiv = $('<div>').addClass('d-flex align-items-center justify-content-between');
  const priorityIcon = $('<i>').addClass(priorityClassName[task.priority]).addClass('pe-1');

  leftContentDiv.append(icon).append(priorityIcon).append(task.name);

  const rightContentDiv = $('<div>').text(task.completedAt);

  const colDiv = $('<div>').addClass('col d-flex align-items-center justify-content-between');
  colDiv.append(leftContentDiv);
  colDiv.append(rightContentDiv);

  const rowDiv = $('<div>').addClass('row').data('id', task.id);
  rowDiv.append(colDiv);

  rowDiv.on('click', (e) => {
    // TODO: 既に開いている && 同じ task.id の場合は閉じる

    // TODO: 既に開いている && 異なる task.id の場合は、書き換える。
  });

  return rowDiv;
};

const toggleRightBar = (e) => {
  const rightBar = $("#Task_RightSideBar");
  const isOpen = rightBar.data("open");

  if (!isOpen) {
    rightBar.addClass("open").data("open", true);
    return;
  }

  // TODO: 開いて表示しているTaskが何なのか？を判別する方法が必要。
  //     | open か close の状態を示す方法として、data-idを付与する方法があるかも？
  // if (e.data('id') == )
};

const generateIncompleteTaskCircleIconElement = (task) => {
  const submitButton = $('<button>', {
    class: 'btn btn-transparent',
    data: { id: task.id },
    on: {
      click: async (e) => {
        e.preventDefault();

        completeTask($(e.currentTarget).data('id'))
          .then((task) => {
            CompleteTasks.unshift(task);
            IncompleteTasks = IncompleteTasks.filter((t) => t.id !== task.id);
          })
          .then(() => initTask())
          .then(() => e.currentTarget.reset());
      },
    },
  });

  submitButton.append($('<i>', { class: 'bi bi-circle' }));

  return submitButton;
};

const generateCompleteTaskCircleIconElement = () => {
  const span = $('<span>', { class: 'btn btn-transparent pe-none' });

  span.append($('<i>', { class: 'bi bi-check-circle-fill' }));

  return span;
};

// ################################################################################
// Projects API
// ################################################################################

const findAllProjects = async () => {
  const response = await getApi('/api/projects');

  return response.json();
};

const createProject = async (params) => {
  const response = await postApi('/api/projects', params);

  return response.json();
};

// ################################################################################
// Tags API
// ################################################################################

const createTag = async (params) => {
  const response = await postApi('/api/tags', params);

  return response.json();
};

// ################################################################################
// Tasks API
// ################################################################################

const findAllTasksByProjectId = async (projectId = undefined) => {
  const response = await getApi('/api/tasks', { projectId });

  return response.json();
};

const findIncompleteTasksByProjectId = async (projectId = undefined) => {
  const response = await getApi('/api/tasks', {
    projectId,
    isCompleted: false
  });

  return response.json();
};

const findCompleteTasksByProjectId = async (projectId = undefined) => {
  const response = await getApi('/api/tasks', {
    projectId,
    isCompleted: true
  });

  return response.json();
};

const getTaskSummary = async (projectId = undefined) => {
  const response = await getApi('/api/tasks/summary', { projectId });

  return response.json();
}

const createTask = async (params) => {
  const response = await postApi('/api/tasks', params);

  return response.json();
};

const updateTask = async (params) => {
  const response = await putApi('/api/tasks', params);

  return response.json();
};

const completeTask = async (id) => {
  const response = await putApi('/api/tasks/done', { id });

  return response.json();
}

// ################################################################################
// API Library
// ################################################################################
const headers = { 'Content-Type': 'application/json' };

const getApi = async (url, params = {}) => {
  return await fetch(`${url}${convertToQueryParamString(params)}`, {
    method: 'GET',
    headers,
  });
};

const postApi = async (url, params = {}) => {
  return await fetch(url, {
    method: 'POST',
    headers,
    body: JSON.stringify(params),
  });
};

const putApi = async (url, params = {}) => {
  return await fetch(url, {
    method: 'PUT',
    headers,
    body: JSON.stringify(params),
  });
};

const deleteApi = async (url, params = {}) => {
  return await fetch(`${url}${convertToQueryParamString(params)}`, {
    method: 'DELETE',
    headers,
  });
};

const convertToQueryParamString = (params) => {
  const purgedParams = Object.fromEntries(Object.entries(params).filter(([, v]) => v));
  const queryParams = new URLSearchParams(purgedParams);

  return !!queryParams ? `?${queryParams}` : ''
};
