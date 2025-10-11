import React from "react";

export default function MovieCard({ title, image }) {
  return (
    <div className="w-40 flex-shrink-0 bg-gray-900 rounded-lg overflow-hidden shadow-lg">
      <img src={image} alt={title} className="w-full h-56 object-cover" />
      <div className="p-2">
        <h3 className="text-sm font-semibold">{title}</h3>
      </div>
    </div>
  );
}
