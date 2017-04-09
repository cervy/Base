public abstract class BaseAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
            class ViewHolder extends RecyclerView.ViewHolder {
                //...
                private SparseArray<View> mViews;
                public View mConvertView;
                private Context mContext;//获取res



                public ViewHolder setText(int viewId, String text) {
                    TextView tv = getView(viewId);
                    tv.setText(text);
                    return this;
                }

                public ViewHolder(Context context,View itemView) {
                    super(itemView);
                    mContext = context;
                    mConvertView = itemView;
                    mViews = new SparseArray<View>();
                }

                public ViewHolder(View itemView) {
                    super(itemView);
                    mConvertView = itemView;
                    mViews = new SparseArray<View>();
                }

                public ViewHolder setImageResource(int viewId, int resId) {
                    ImageView view = getView(viewId);
                    view.setImageResource(resId);
                    return this;
                }


                public <T extends View> T getView(int viewId) {
                    View view = mViews.get(viewId);
                    if (view == null) {
                        view = mConvertView.findViewById(viewId);
                        mViews.put(viewId, view);
                    }
                    return (T) view;
                }

                public ViewHolder setText(int viewId, String text) {
                    TextView tv = getView(viewId);
                    tv.setText(text);
                    return this;
                }

                public ViewHolder setImageResource(int viewId, int resId) {
                    ImageView view = getView(viewId);
                    view.setImageResource(resId);
                    return this;
                }

                public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
                    ImageView view = getView(viewId);
                    view.setImageBitmap(bitmap);
                    return this;
                }

                public ViewHolder setImageDrawable(int viewId, Drawable drawable) {
                    ImageView view = getView(viewId);
                    view.setImageDrawable(drawable);
                    return this;
                }

                public ViewHolder setVisible(int viewId, boolean visible) {
                    View view = getView(viewId);
                    view.setVisibility(visible ? View.VISIBLE : View.GONE);
                    return this;
                }

                public ViewHolder setChecked(int viewId, boolean checked) {
                    Checkable view = (Checkable) getView(viewId);
                    view.setChecked(checked);
                    return this;
                }


                public ViewHolder setOnTouchListener(int viewId,
                                                     View.OnTouchListener listener) {

                    getView(viewId).setOnTouchListener(listener);

                    return this;
                }

                public final LinkedHashSet<Integer> childClickViewIds, itemChildLongClickViewIds;// TODO: 009/9/4/2017 优化重复设置item子View事件


                public ViewHolder setOnLongClickListener(int viewId,
                                                         View.OnLongClickListener listener) {


                    getView(viewId).setOnLongClickListener(listener);
                    return this;
                }
            }

            protected Context mContext;
            public int mLayoutId;
            public List<T> mDatas;
            protected LayoutInflater mInflater;




            public BaseAdapter(int layoutId, List<T> datas) {

                mLayoutId = layoutId;
                mDatas = datas;
            }

            @Override
            public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
                return
                        new ViewHolder(//parent.getContext(),
                                (mInflater = LayoutInflater.from(this.mContext = parent.getContext())).inflate(mLayoutId,parent,false)
                        );
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
//                holder.updatePosition(position);
                convert(holder, mDatas.get(position));
            }

            public abstract void convert(ViewHolder holder, T t);

            @Override
            public int getItemCount() {
                return mDatas.size();
            }
        }
