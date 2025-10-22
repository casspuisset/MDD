export interface Article {
  id: number;
  title: string;
  description: string;
  topic_id: number;
  creator_id: number;
  created_at: Date;
  updated_at: Date;
}
