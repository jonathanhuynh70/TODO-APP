export interface TaskDTO {
  id?: number; // Optional because we tasks wont have an id upon creation
  title: string;
  description?: string; // Optional, dont need a description
  dateCreated: Date;
  completed: Boolean;
}

export const mapTaskApiResponseToTaskDTO = (data: any) => {
    return {
        id: data.id,
        title: data.title,
        description: data.description ?? null, 
        dateCreated: new Date(data.dateCreated),
        completed: data.completed
    }
}