export interface Article {
  id: number;
  title: string;
  description: string;
  topicId: number;
  topicName: string;
  creatorId: number;
  creatorUsername: string;
  createdAt: Date;
  updatedAt: Date;
}
