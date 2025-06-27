import axios from "axios";
import type { TaskDTO } from '../DTO/TaskDTO.ts'
import { mapTaskApiResponseToTaskDTO } from '../DTO/TaskDTO.ts'

const REST_API_BASE_URL = 'http://localhost:8080/tasksApi';

// Private method that will convert the response.data array into an array of Tasks
const convertDataToTaskDTO = (data: any) => {
    let taskList: TaskDTO[] = [];
    data.forEach((task: any) => taskList.push(mapTaskApiResponseToTaskDTO(task)))
    return taskList;
}

// Returns a list of the task
export const taskList = async () => {
    const response = await axios.get(REST_API_BASE_URL+"/getAllTasks")
    return convertDataToTaskDTO(response.data);
}
// method that will create the task and reload the page
export const createTask = async (formValues: {
        title: string,
        description: string,
    }) => {
    let response;
    try {
        response = await axios.post(REST_API_BASE_URL+"/create", formValues);
    } catch (error: any){
        console.log("Error with creating task")
        console.log(error)
        throw new Error(error.message)
    }
    window.location.reload()
}

// Returns a boolean on whether the update was successful
export const updateTask = async (task: TaskDTO) => {
    try {
        await axios.put(REST_API_BASE_URL+"/update", task)
        window.location.reload()
    } catch (error){
        console.log("Updating todo item failed", error)
        throw new Error("Updating todo item failed")
    }
}

// Returns a boolean on whether the delete was successful
export const deleteTask = (id: number) => {
    return axios.delete(REST_API_BASE_URL+`/${id}`);
}