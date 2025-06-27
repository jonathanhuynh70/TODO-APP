import { useEffect, useState } from 'react';
import { createTask, taskList, updateTask } from '../services/TaskService.ts';
import type { TaskDTO } from '../DTO/TaskDTO.ts'
import ToDoItem from './ToDoItem.tsx';
import Modal from './Modal.tsx'

const ToDoList = () => {
  const [tasks, setTasks] = useState<TaskDTO[]>([]);
  const [taskToEdit, setTaskToEdit] = useState<TaskDTO>();
  const [modalOpen, setModalOpen] = useState(false);

  useEffect(() => {
    taskList().then((response: TaskDTO[]) =>{
      setTasks(response)
    }).catch(error => console.log(error))
  }, []);

  const handleEdit = (task: TaskDTO) => {
    setTaskToEdit(task);
    setModalOpen(true);
  }
  const handleSubmit = async (formValues: {
        title: string,
        description: string,
    }) => {
      if (taskToEdit){
        taskToEdit.title = formValues.title;
        taskToEdit.description = formValues.description;
        await updateTask(taskToEdit);
      } else {
        await createTask(formValues)
      };
    }


  return (
    <div className='container'>
      <h2>To Do List</h2>
        <table>
          <thead>
            <tr>
              <th>Completed</th>
              <th>Title</th>
              <th>Description</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {tasks.map(task => (<ToDoItem task={task} setTaskToEdit={handleEdit}/>))}
          </tbody>
        </table>
        <button onClick={() => setModalOpen(true)} className="btn">
          Add
        </button>
        {modalOpen && (
          <Modal
            closeModal={() => {
              setModalOpen(false);
              setTaskToEdit(undefined);
            }}
            onSubmit={handleSubmit}
            task={taskToEdit}
          />
        )}
    </div>
  );
}

export default ToDoList;