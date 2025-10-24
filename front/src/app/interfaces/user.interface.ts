import { Topic } from './topic.interface';

export interface User {
  id: number;
  name: string;
  email: string;
  topics: Topic[];
  created_at: Date;
  updated_at: Date;
}
