export type task = {
  id: number;
  title: string;
  description: string;
  createdDate: Date;
  completedDate?: Date; // Optional property
  deadline: Date;
  type: String;
};;