import type { TaskDTO } from '../DTO/TaskDTO.ts'
import { updateTask, deleteTask } from '../services/TaskService.ts'

const TodoItem = (props:{task: TaskDTO, setTaskToEdit: Function}) => {
    const { task, setTaskToEdit } = props

    const removeTask = () => {
        deleteTask(task.id as number).then(() => window.location.reload()
        ).catch(error => {
            console.log("Deleting To Do item failed", error)
            throw new Error("Deleting To Do item failed")
        })
    }
   
    return (
        <>
            <tr key={task.id}>
                <td>
                <input 
                    type="checkbox"
                    checked={task.completed as boolean}
                    onChange={() => {
                        task.completed = !task.completed;
                        updateTask(task)
                    }}
                />
                </td>
                <td><span>{task.title}</span></td>
                <td>{task.description}</td>
                <td><button onClick={() => removeTask()}>X</button> <button onClick={() => setTaskToEdit(task)}>Edit</button></td>
            </tr>
        </>
    );
}
export default TodoItem;