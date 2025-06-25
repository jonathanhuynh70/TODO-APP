import { useEffect, useState } from 'react';
import type { task } from "./task.ts"

const TaskList = () => {
  const [tasks, setTasks] = useState<task[]>([]);

  useEffect(() => {
    fetch('http://localhost:8080/tasks/getAllTasks', {
        method: 'GET',
        headers: {
        'Access-Control-Allow-Origin': '*',
        'Content-Type': 'application/json',
      }})
      .then(response => {
        console.log(response)
        response.json()
    })
      .catch(error => console.error('Error fetching users:', error));
  }, []);

  return (
    <div>
      <h2>Tasks</h2>
      <ul>
        {tasks.map(task => (
          <li key={task.id}>{task.description}</li>
        ))}
      </ul>
    </div>
  );
}

export default TaskList;