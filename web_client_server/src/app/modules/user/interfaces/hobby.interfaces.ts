export interface IUserHobby {
  id: string;
  description: string;
}

export interface IUserHobbyItem {
  id: string;
  userId: string;
  description: string;
}

export interface IUserHobbyUpdate {
  id?: string;
  description?: string;
  updatedBy?: string;
}

export interface IUserHobbyCreate {
  userId?: string;
  description?: string;
}
